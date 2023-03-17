package ro.unibuc.hello.data;

import org.springframework.data.annotation.Id;
import ro.unibuc.hello.dto.Customer;

import java.util.ArrayList;

public class CustomerEntity {

    @Id
    public long customer_id;
    public String name;
    public ArrayList<Customer> customers = new ArrayList<>();
    private static long counter = 1;

    public CustomerEntity() {
    }

    public CustomerEntity(String name, ArrayList<Customer> customers) {
        this.name = name;
        this.customers = customers;
        this.customer_id=counter;
        counter++;
    }



    public Customer getCustomer(long customer_id){
        for (Customer c: customers) {
            if(c.getCustomer_id()==customer_id)
                return c;

        }
        return null;
    }

    public ArrayList<Customer> delCustomer(long customer_id){
        System.out.println("id= "+customer_id);
        for (int i=0; i<customers.size(); i++) {
            if(customers.get(i).getCustomer_id()==customer_id)
            {
                customers.remove(i);
                break;
            }

        }
        return customers;
    }

    public void delCustomers(){
        customers=new ArrayList<Customer>();
    }

    public void addCustomer(Customer c){
        this.customers.add(c);
    }

    public long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(long customer_id) {
        this.customer_id = customer_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(ArrayList<Customer> customers) {
        this.customers = customers;
    }

    @Override
    public String toString() {
        return "CustomerEntity{" +
                "customer_id=" + customer_id +
                ", name='" + name + '\'' +
                ", customers=" + customers +
                '}';
    }
}
