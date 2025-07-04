package com.example.concertReservation.application.waitingroom;

import com.example.concertReservation.application.waitingroom.dto.WaitingRoomEntryResponse;
import com.example.concertReservation.domain.waitingQueue.WaitingQueueService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.example.concertReservation.infrastructure.redis.RedisKeyType.CONCERT_QUEUE_WAIT_KEY;
import static com.example.concertReservation.infrastructure.redis.RedisKeyType.CONCERT_QUEUE_WAIT_TOKEN_KEY;

@Service
@Transactional
@RequiredArgsConstructor
public class WaitingRoomApplicationService {

    private final WaitingQueueService waitingQueueService;

    @Transactional
    public WaitingRoomEntryResponse enterWaitingRoom(Long concertId, Long memberId) {
        String queueName = CONCERT_QUEUE_WAIT_KEY.getKey(concertId);

        long currentRank = waitingQueueService.getWaitingRank(queueName, memberId);
        if (currentRank != -1) {
            return WaitingRoomEntryResponse.of(currentRank);
        }

        long rank = waitingQueueService.addToWaitingQueue(queueName, memberId, System.currentTimeMillis());
        return WaitingRoomEntryResponse.of(rank);
    }

    public WaitingRoomEntryResponse getRank(Long concertId, Long memberId) {
        return WaitingRoomEntryResponse.of(waitingQueueService.getWaitingRank(CONCERT_QUEUE_WAIT_KEY.getKey(concertId), memberId));
    }

    public Boolean isAllowedByToken(Long concertId, Long memberId, String token) {
        if (token.isEmpty()) {
            return false;
        }
        return generateToken(concertId, memberId).equalsIgnoreCase(token);
    }


    public String generateToken(Long concertId, Long memberId) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            String  input = CONCERT_QUEUE_WAIT_TOKEN_KEY.getKey(concertId, memberId);
            byte[] encodedHash = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte aByte: encodedHash) {
                hexString.append(String.format("%02x", aByte));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }


}
