package com.wizarpos.wizarviewagent.aidl;

interface ICustomSnApi {

//    public static final int ERR_VERIFY_PERMISSION_FAILED = -1;
//    public static final int ERR_ILLEGAL_PARAMER = -2;
//    public static final int ERR_BIND_FAILED_HSM = -3;
//    public static final int ERR_BIND_FAILED_PROP = -4;
    /**
     * @param SN        0
     * @permission      android.permission.CLOUDPOS_UPDATE_CUSTOM_SERIAL_NUMBER
     * @return          0 if success or negative int if falied(check error code.)
     *
     */
    int writerSn(String sn);


}
