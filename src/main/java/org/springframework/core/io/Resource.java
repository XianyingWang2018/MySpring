package org.springframework.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * 资源的抽象访问接口
 */
public interface Resource {

    InputStream getInputStream() throws IOException;
}
