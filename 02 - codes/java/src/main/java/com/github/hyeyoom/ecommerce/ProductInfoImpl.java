package com.github.hyeyoom.ecommerce;

import ecommerce.ProductInfoGrpc;
import ecommerce.ProductInfoOuterClass;
import io.grpc.Status;
import io.grpc.StatusException;
import io.grpc.stub.StreamObserver;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ProductInfoImpl extends ProductInfoGrpc.ProductInfoImplBase {

    private final Map<String, ProductInfoOuterClass.Product> productMap = new HashMap<>();

    @Override
    public void addProduct(ProductInfoOuterClass.Product request, StreamObserver<ProductInfoOuterClass.ProductID> responseObserver) {
        final UUID uuid = UUID.randomUUID();
        final String randomUUIDString = uuid.toString();
        final ProductInfoOuterClass.Product product = request.toBuilder().setId(randomUUIDString).build();
        productMap.put(randomUUIDString, product);
        final ProductInfoOuterClass.ProductID id = ProductInfoOuterClass.ProductID.newBuilder().setValue(randomUUIDString).build();
        responseObserver.onNext(id);
        responseObserver.onCompleted();
        System.out.println("상품 추가됨: " + id.getValue());
    }

    @Override
    public void getProduct(ProductInfoOuterClass.ProductID request, StreamObserver<ProductInfoOuterClass.Product> responseObserver) {
        final String id = request.getValue();
        if (productMap.containsKey(id)) {
            System.out.println("상품 조회됨: " + id);
            responseObserver.onNext(productMap.get(id));
            responseObserver.onCompleted();
            return;
        }
        responseObserver.onError(new StatusException(Status.NOT_FOUND));
    }
}
