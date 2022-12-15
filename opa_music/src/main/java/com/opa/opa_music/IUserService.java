package com.opa.opa_music;

//Interface User Service
public interface IUserService {
    //Method wich save a user into the user repository, return the id of the user.
    public Integer saveUser(User user);

    //Method wich is verifying if the username is already taken or not.
    public boolean isUsernameAlreadyTake(String name);
}
