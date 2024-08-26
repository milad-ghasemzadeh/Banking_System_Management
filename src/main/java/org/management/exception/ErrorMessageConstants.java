package org.management.exception;

import org.springframework.http.HttpStatus;

public class ErrorMessageConstants {


    public interface InternalServer {
        String message = "ارور داخلی!!";
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public interface NotNull {
        String message = "به خالی بودن یا درست وارد کردن این فیلد توجه کنید";
        HttpStatus status = HttpStatus.BAD_REQUEST;
    }

    public interface CouldNotFindBankAccount {
        String message = " چنین شماره حساب بانکی در سیستم وجود ندارد";
        String developer_message = "we couldn't find any Bank Account with this ID";
        HttpStatus status = HttpStatus.NOT_FOUND;
    }

    public interface NotEnoughMoney {
        String message = " مبلغ درخواست شده برای برداشت بیشتر از مقدار پول پس انداز شده است";
        String developer_message = "We don't have enough money in bank account";
        HttpStatus status = HttpStatus.BAD_REQUEST;
    }

    public interface NotExistID {
        String message = "شما فقط میتوانید اطلاعات مشترکی را اپدیت و یا دیلیت کنید در صورتی که شماره منحصر بفرد در سیستم وجود داشته باشد";
        String developer_message = "you only can update or delete one the existing account in the system(ID account)";
        HttpStatus status = HttpStatus.NOT_FOUND;
    }

    public interface UniqueByNationalCode {
        String message = "کد ملی مشتری باید منحصر بفرد باشد";
        String developer_message = "the national code must be unique";
        HttpStatus status = HttpStatus.BAD_REQUEST;
    }

    public interface CannotChangeNationalCode {
        String message = "شما نمیتوانید کد ملی خود را تغییر بدهید";
        String developer_message = "you can't change your national Code number";
        HttpStatus status = HttpStatus.BAD_REQUEST;
    }
}
