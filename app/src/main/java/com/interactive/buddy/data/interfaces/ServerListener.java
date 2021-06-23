package com.interactive.buddy.data.interfaces;

import com.interactive.buddy.data.model.LoggedInUser;

public interface ServerListener {
    void onSuccess(LoggedInUser loggedInUser);
    void onError(Exception exception);
}
