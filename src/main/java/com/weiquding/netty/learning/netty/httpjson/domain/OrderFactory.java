package com.weiquding.netty.learning.netty.httpjson.domain;

/**
 * @author wubai
 * @date 2018/9/22 20:27
 */
public class OrderFactory {

    public static Order create(long orderId){
        Order order = new Order();
        order.setOrderNumber(orderId);
        order.setTotal(111f);
        Address address = new Address();
        address.setCity("shanghai");
        address.setCountry("china");
        address.setPostCode("1111111");
        address.setState("shanghai");
        address.setStreet1("gonghua");
        order.setBillTo(address);
        order.setShipTo(address);
        Customer customer = new Customer();
        customer.setCustomerNumber(orderId);
        customer.setFirstName("wu");
        customer.setLastName("baiyi");
        order.setCustomer(customer);
        order.setShipping(Shipping.INTERNATIONAL_MAIL);
        return order;
    }
}
