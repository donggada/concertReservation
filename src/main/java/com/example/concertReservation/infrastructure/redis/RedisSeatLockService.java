package com.example.concertReservation.infrastructure.redis;

import com.example.concertReservation.domain.seat.SeatLockService;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static com.example.concertReservation.infrastructure.redis.RedisKeyType.SEAT_LOCK_KEY;

@Service
@RequiredArgsConstructor
public class RedisSeatLockService implements SeatLockService {
    private final RedissonClient redissonClient;
    private static final long LOCK_LEASE_TIME = 10;

    @Override
    public boolean lockSeat(Long seatId, Long memberId) {
        String lockKey = SEAT_LOCK_KEY.getKey(seatId);
        RLock lock = redissonClient.getLock(lockKey);

        try {
            boolean locked = lock.tryLock(0, LOCK_LEASE_TIME, TimeUnit.MINUTES);

            if (locked) {
                redissonClient.getBucket(lockKey + ":owner").set(memberId, LOCK_LEASE_TIME, TimeUnit.MINUTES);
                return true;
            }
            return false;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    @Override
    public boolean releaseSeatLock(Long seatId) {
        String lockKey = SEAT_LOCK_KEY.getKey(seatId);
        RLock lock = redissonClient.getLock(lockKey);
        if (lock.isLocked() && lock.isHeldByCurrentThread()) {
            lock.unlock();
            redissonClient.getBucket(lockKey + ":owner").delete();
            return true;
        }

        return !lock.isLocked();
    }

    @Override
    public boolean isLockedBy(Long seatId, Long memberId) {
        String lockKey = SEAT_LOCK_KEY.getKey(seatId);
        RLock lock = redissonClient.getLock(lockKey);

        if (!lock.isLocked()) {
            return false;
        }

        Long ownerId = (Long) redissonClient.getBucket(lockKey + ":owner").get();
        return ownerId != null && ownerId.equals(memberId);
    }
}
