package com.elebras1.message.controller;

import com.elebras1.message.exception.AuthenticationException;

public interface ILoginController {

    void login(String tag, String password) throws AuthenticationException;
}
