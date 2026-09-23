package stax.payment.processor.service;

import java.util.concurrent.atomic.AtomicInteger;

public class ProcessingResult {

    private AtomicInteger processed;
    private AtomicInteger rejected;
    private AtomicInteger failed;


    public void incrementProcessed() {
        processed.addAndGet(1);
    }

    public void incrementRejected() {
        rejected.addAndGet(1);
    }

    public void incrementFailed(){
        failed.addAndGet(1);
    }
}
