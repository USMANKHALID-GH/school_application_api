package com.usman.enums;

public enum ResponseMessageEnum {
    BACK_SYSTEM_ERROR_MSG_001("back.system.error.msg.001", "Sistemsel bir hata alınmıştır, Daha sonra tekrar deneyiniz."),

    BACK_JSON_CONVERTOR_MSG_001("back.user_convertor.msg.001", "Json convertor yaparken hata alinmistir."),


    BACK_ROLE_MSG_001("back.role.msg.001", "Rol Tanımı bulunamamıştır."),
    BACK_ROLE_MSG_002("back.role.msg.002", "Bu isimde rol tanımı var, farklı bir isim giriniz."),
    BACK_ROLE_MSG_003("back.role.msg.003", "Rolü, eklenmiş olan kullanıcılardan çıkardıktan sonra silebilirsiniz."),



    BACK_PRIVILEGE_MSG_001("back.privilege.msg.001", "Yetki Tanımı bulunamamıştır."),
    BACK_PRIVILEGE_MSG_002("back.privilege.msg.002", "Yetkiyi Eklenmiş olan rollerden çıkardıktan sonra silebilirsiniz."),
    BACK_PRIVILEGE_MSG_003("back.privilege.msg.003", "Bu isimde yetki tanımı var, farklı bir isim giriniz."),



    BACK_PARAMETER_MSG_001("back.parameter.msg.001", "Parametre bulunamadı."),
    BACK_PARAMETER_MSG_002("back.parameter.msg.002", "Parameter başarılı bir şekilde güncellendi."),
    BACK_PARAMETER_MSG_003("back.parameter.msg.003", "Parameter başarılı bir şekilde oluşturuldu ya da zaten var."),
    BACK_PARAMETER_MSG_004("back.parameter.msg.004", "Parameter başarılı bir şekilde silinmiştir."),
    BACK_PARAMETER_MSG_005("back.parameter.msg.005", "Parametre zaten kayıtlı."),




    BACK_USER_MSG_001("back.user.msg.001", "Kullanıcı bulunamamıştır."),
    BACK_USER_MSG_002("back.user.msg.002", "Bu Telefon Sistemde Kayıtlıdır."),
    BACK_USER_MSG_003("back.user.msg.003", "Bu Mail Sistemde Kayıtlıdır."),
    BACK_USER_MSG_004("back.user.msg.004", "Bu kullanıcı aktif değildir."),
    BACK_USER_MSG_007("back.user.msg.007", "Email veya şifre hatalı."),
    BACK_USER_MSG_006("back.user.msg.006", " Telefon veya email zaten kullanımda"),



    BACK_VALIDATION_MSG_001("back.validation.msg.001", "Kullanıcı bilgileri hatalı (TC - AD - SOYAD - YIL)"),

    BACK_FILE_MSG_001("back.file.msg.001", "Dosya kaydedilken hata alinmistir.  "),
    BACK_FILE_MSG_002("back.file.msg.002", "Dosya silinirken hata alinmistir.  "),
    BACK_FILE_MSG_003("back.file.msg.003", "Dosya boyutu 10MB'dan fazla olamaz. "),
    BACK_FILE_MSG_004("back.file.msg.004", "Dosya bos olamaz."),


    ;

    private final String message;
    private final String messageDetail;

    ResponseMessageEnum(String message, String messageDetail) {
        this.message = message;
        this.messageDetail = messageDetail;
    }

    public String message() {
        return this.message;
    }

    public String messageDetail() {
        return this.messageDetail;
    }
}
