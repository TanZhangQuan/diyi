package com.lgyun.common.support;

import java.io.OutputStream;

/**
 * A factory for creating MultiOutputStream objects.
 *
 * @author liangfeihu
 */
public interface IMultiOutputStream {

    /**
     * Builds the output stream.
     *
     * @param params the params
     * @return the output stream
     */
    OutputStream buildOutputStream(Integer... params);

}
