package ro.unibuc.hello.service;

import ro.unibuc.hello.dto.ResponseDto;

public interface MockDbService {

    ResponseDto mockDatabase();

    boolean isDatabaseEmpty();

    void checkDatabase();

}
