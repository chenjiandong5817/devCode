/*
 * SummerSoft  YueYue-Travel Platform
 * <p>
 * Copyright (c) 2017-2018  SummerSoft Technology (Xiamen) Co.,LTD
 * All rights reserved.
 * <p>
 * This software is the confidential and proprietary information of SummerSoft
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with SummerSoft.
 * <p>
 */

package com.plugs.thread;

import com.plugs.module_user.services.UserElectronInvoiceService;
import net.sf.json.JSONObject;

public class InvoiceQueryThread implements Runnable{


    private UserElectronInvoiceService userElectronInvoiceService;
    private String uuid;
    private Integer type;

    public InvoiceQueryThread(UserElectronInvoiceService userElectronInvoiceService, String uuid, Integer type) {
        this.userElectronInvoiceService = userElectronInvoiceService;
        this.uuid = uuid;
        this.type = type;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        boolean bl;
        Integer i = 0;
        try {
            do{
                Thread.sleep(5000L);
                JSONObject json = userElectronInvoiceService.electronGenerateQuery(this.uuid, this.type);
                bl = json.getBoolean("status");
                i++;
            }while (!bl && i < 5);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
