package com.example.concertReservation.domain.waitingQueue;

public interface WaitingQueueService {

    long addToWaitingQueue(String queueName, Long memberId, double score);

    void popFromWaitingQueue(String queueName, long count);

    long getWaitingRank(String queueName, Long memberId);

    long getWaitingQueueSize(String queueName);
}
