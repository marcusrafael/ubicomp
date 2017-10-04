package com.ubicomp.context.cep.subscriber;

public interface StatementSubscriber {

    /**
     * Get the EPL Stamement the Subscriber will listen to.
     * @return EPL Statement
     */
    public String getStatement();

}
