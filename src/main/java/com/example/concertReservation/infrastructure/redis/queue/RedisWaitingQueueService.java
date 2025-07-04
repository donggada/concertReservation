package com.example.concertReservation.infrastructure.redis.queue;

import com.example.concertReservation.domain.waitingQueue.WaitingQueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
@RequiredArgsConstructor
public class RedisWaitingQueueService implements WaitingQueueService {
    private final ZSetOperations<String, String> zSetOperations;

    @Override
    public long addToWaitingQueue(String queueName, Long memberId, double score) {
        zSetOperations.add(queueName, String.valueOf(memberId), score);
        return getWaitingRank(queueName, memberId);
    }

    @Override
    public void popFromWaitingQueue(String queueName, long count) {
        Set<String> users = zSetOperations.range(queueName, 0, count - 1);
        if (users != null && !users.isEmpty()) {
            zSetOperations.removeRange(queueName, 0, count - 1);
        }
    }

    @Override
    public long getWaitingRank(String queueName, Long memberId) {
        Long rank = zSetOperations.rank(queueName, String.valueOf(memberId));
        return rank != null ? rank + 1 : -1;
    }

    @Override
    public long getWaitingQueueSize(String queueName) {
        Long size = zSetOperations.size(queueName);
        return size != null ? size : 0;
    }
}
