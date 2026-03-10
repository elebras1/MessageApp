package com.elebras1.message.controller;

import com.elebras1.message.exception.RegistrationException;

public interface ISubscribeController {

    void subscribe(String username, String tag, String password) throws RegistrationException;
}
