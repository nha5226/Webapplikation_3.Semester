package Webapplication.Assignments02.Vorbereitung;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class ReadWriteCounter {
    ReadWriteLock lock = new ReentrantReadWriteLock();
    private int count = 0;
    public int incrementAndGetCount() {
        lock.writeLock().lock();
        try {
            count++; // critical section
            return count;
        } finally {
            lock.writeLock().unlock();
        }
    }
    public int getCount() {
        lock.readLock().lock();
        try {
            return count;
        } finally {
            lock.readLock().unlock();
        }
    }
}
