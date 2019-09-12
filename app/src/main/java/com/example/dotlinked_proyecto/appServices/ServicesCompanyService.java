package com.example.dotlinked_proyecto.appServices;

import com.example.dotlinked_proyecto.API.Connection.Connection;
import com.example.dotlinked_proyecto.API.RetrofitSources.IServicesApiCalls;
import com.example.dotlinked_proyecto.bean.Appointment;
import com.example.dotlinked_proyecto.bean.Person;
import com.example.dotlinked_proyecto.bean.Service;
import com.example.dotlinked_proyecto.bean.ServiceInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;

public class ServicesCompanyService implements IServicesApiCalls {
  private IServicesApiCalls servicesApiCalls;

  public ServicesCompanyService() {
    Retrofit retrofit = Connection.getRetrofitClient();
    this.servicesApiCalls = retrofit.create(IServicesApiCalls.class);
  }

  @Override
  public Call<List<Service>> getServicesByCompanyId(String companyId, String token) {
    return servicesApiCalls.getServicesByCompanyId(companyId, token);
  }

  @Override
  public Call<List<Appointment>> getReservedServiceOfUser(String rol, String companyId, String dateInit, String dateEnd, String token) {
    return servicesApiCalls.getReservedServiceOfUser(rol, companyId, dateInit, dateEnd, token);
  }

  @Override
  public Call<List<Person>> listTenantByContact(String companyId, String rol, String token) {
    return servicesApiCalls.listTenantByContact(companyId, rol, token);
  }

  @Override
  public Call<List<ServiceInfo>> listScheduleService(String companyId, String serviceId, String date, String personId, String token) {
    return servicesApiCalls.listScheduleService(companyId, serviceId, date, personId, token);
  }

  @Override
  public Call<String> createUpdateAppointment(int appointmentId,
                                              int serviceId,
                                              int personId,
                                              String dateFrom,
                                              String token) {
    return servicesApiCalls.createUpdateAppointment(appointmentId, serviceId, personId, dateFrom, token);
  }

  @Override
  public Call<String> deleteAppointment(int appointmentId, int serviceId, int personId, String dateFrom, String token) {
    return servicesApiCalls.deleteAppointment(appointmentId, serviceId, personId, dateFrom, token);
  }


}
