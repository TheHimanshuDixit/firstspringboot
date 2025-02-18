package com.himanshu.journalApp.service;

import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import com.himanshu.journalApp.entity.User;

public class UserArgumentsProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context) {
        return Stream.of(
                Arguments.of(User.builder().username("Harsh").password("Harsh").build()),
                Arguments.of(User.builder().username("Himanshu").password("Himanshu").build()),
                Arguments.of(User.builder().username("Rahul").password("Rahul").build()));
    }
}
