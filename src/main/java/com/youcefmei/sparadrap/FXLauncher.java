package com.youcefmei.sparadrap;

import com.youcefmei.sparadrap.exception.DuplicateException;
import com.youcefmei.sparadrap.exception.InvalidDateException;
import com.youcefmei.sparadrap.exception.InvalidInputException;
import com.youcefmei.sparadrap.exception.PaymentException;

import java.io.IOException;

public class FXLauncher {
    public static void main(String[] args) throws InvalidInputException, PaymentException, InvalidDateException, DuplicateException, IOException {
        Main.main(args);
    }
}
