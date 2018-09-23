package com.weiquding.netty.learning.netty.codecs.marshalling.factory;

import io.netty.handler.codec.marshalling.*;
import org.jboss.marshalling.*;

import java.io.IOException;

/**
 * @author wubai
 * @date 2018/9/22 11:30
 */
public class MarshallingCodecFactory {

    public static MarshallingDecoder buildMarshallingDecoder(){
        final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        final UnmarshallerProvider provider = new DefaultUnmarshallerProvider(marshallerFactory, configuration);
        final MarshallingDecoder decoder = new MarshallingDecoder(provider, 1024);
        return decoder;
    }

    public static MarshallingEncoder buildMarshallingEncoder(){
        final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        final MarshallerProvider provider = new DefaultMarshallerProvider(marshallerFactory, configuration);
        final MarshallingEncoder encoder = new MarshallingEncoder(provider);
        return  encoder;
    }

    public static Marshaller buildMarshaller() throws IOException {
        // Get the factory for the "serial" marshalling protocol
        final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
        // Create a configuration
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        // Use version 5 
        configuration.setVersion(5);
        final Marshaller marshaller = marshallerFactory.createMarshaller(configuration);
        return marshaller;
    }

    public static Unmarshaller buildUnmarshaller() throws IOException {
        // Get the factory for the "serial" marshalling protocol
        final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
        // Create a configuration
        final MarshallingConfiguration configuration = new MarshallingConfiguration();
        configuration.setVersion(5);
        final Unmarshaller unmarshaller = marshallerFactory.createUnmarshaller(configuration);
        return unmarshaller;
    }
}
