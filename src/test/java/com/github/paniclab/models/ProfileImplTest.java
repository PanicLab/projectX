package com.github.paniclab.models;

import org.junit.Test;

import static org.junit.Assert.*;

public class ProfileImplTest {
    @org.junit.Test
    public void userName() throws Exception {
    }

    @org.junit.Test
    public void password() throws Exception {
    }

    @org.junit.Test
    public void getSafePassword() throws Exception {
    }

    @org.junit.Test
    public void isEmpty() throws Exception {
    }

    @org.junit.Test
    public void hashCode_test() throws Exception {
    }

    @org.junit.Test
    public void equals_reflexive() throws Exception {
        Profile x = Profile.valueOf("Vladimir");
        assertTrue(x.equals(x));
    }

    @Test
    public void equals_symmetric() throws Exception {
        Profile x = Profile.valueOf("Vladimir");
        Profile y = Profile.valueOf("Vladimir");
        assertTrue(x.equals(y));
        assertTrue(y.equals(x));
    }

    @Test
    public void equals_transitive() throws Exception {
        Profile x = Profile.valueOf("Vladimir");
        Profile y = Profile.valueOf("Vladimir");
        Profile z = Profile.valueOf("Vladimir");
        assertTrue(x.equals(y));
        assertTrue(y.equals(z));
        assertTrue(z.equals(x));
    }

    @Test
    public void equals_consistent() throws Exception {
        Profile x = Profile.valueOf("Vladimir");
        Profile y = Profile.valueOf("Vladimir");
        for(int i = 0; i < 100; i++) {
            assertTrue(x.equals(y));
        }
    }

    @Test
    public void equals_takesNull_returnFalse() throws Exception {
        Profile x = Profile.valueOf("Vladimir");
        Profile y = null;
        assertFalse(x.equals(y));
    }

    @Test
    public void equals_emptyWithEmpty_returnTrue() throws Exception {
        Profile x = Profile.valueOf("");
        assertTrue(x.equals(Profile.EMPTY));
    }

    @Test
    public void equals_emptyWithNoEmpty_returnFalse() throws Exception {
        Profile x = Profile.valueOf("John Doe");
        assertFalse(x.equals(Profile.EMPTY));
    }

    @org.junit.Test
    public void toString_test() throws Exception {
    }

}