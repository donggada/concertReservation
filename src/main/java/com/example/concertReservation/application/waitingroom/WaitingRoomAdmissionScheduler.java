package com.example.concertReservation.application.waitingroom;

import com.example.concertReservation.domain.concert.Concert;
import com.example.concertReservation.domain.concert.ConcertRepository;
import com.example.concertReservation.domain.waitingQueue.WaitingQueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.example.concertReservation.infrastructure.redis.RedisKeyType.CONCERT_QUEUE_WAIT_KEY;

@Component
@RequiredArgsConstructor
public class WaitingRoomAdmissionScheduler {
    private final WaitingQueueService waitingQueueService;
    private final ConcertRepository concertRepository;
    private static final int ADMISSION_CAPACITY_PER_RUN = 10;

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void admitUsersFromWaitingQueue() {
        List<Concert> activeConcerts = concertRepository.findActiveConcertsForAdmission(LocalDateTime.now());

        activeConcerts.forEach(concert -> {
            String queueName = CONCERT_QUEUE_WAIT_KEY.getKey(concert.getId());
            waitingQueueService.popFromWaitingQueue(queueName, ADMISSION_CAPACITY_PER_RUN);
        });
    }

//    @Scheduled(cron = "0 */5 * * * *")
    @Transactional
    public void closeExpiredConcertQueues() {
        List<Concert> closedConcertList = concertRepository.findClosedConcertsNeedingQueueCleanup(LocalDateTime.now());

        for (Concert concert : closedConcertList) {
            String queueName = CONCERT_QUEUE_WAIT_KEY.getKey(concert.getId());
            long queueSize = waitingQueueService.getWaitingQueueSize(queueName);
            waitingQueueService.popFromWaitingQueue(queueName, queueSize);
            concert.markClosed();
        }
    }
}
