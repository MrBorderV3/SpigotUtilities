package me.border.spigotutilities.cache;

import java.util.Calendar;
import java.util.Date;

public class CachedObject implements Cacheable {
    private Date dateOfExpiration = null;
    public Object object;
    private int hash = 0;

    public CachedObject(Object obj, int minutesToLive) {
        if (obj == null){
            throw new NullPointerException("Object cant be null");
        }
        this.object = obj;
        // minutesToLive of 0 means it lives on indefinitely.
        if (minutesToLive != 0) {
            dateOfExpiration = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(dateOfExpiration);
            cal.add(Calendar.MINUTE, minutesToLive);
            dateOfExpiration = cal.getTime();
        }
    }

    public boolean isExpired() {
        if (dateOfExpiration != null) {
            Date currentTime = new Date();
            return dateOfExpiration.before(currentTime);
        }

        return false;
    }

    public Object getObject(){
        return object;
    }

    public boolean equals(Object o){
        if (o == this)
            return true;
        if (o instanceof Cacheable){
            Cacheable cacheable = (Cacheable) o;
            return cacheable.getObject().equals(this.getObject());
        }

        return false;
    }

    public int hashCode(){
        if (this.hash == 0 || this.hash == 385){
            this.hash = 475 + getObject().hashCode();
        }

        return hash;
    }
}
