package com.example.fihhuda.quran.viewsModel;

import androidx.lifecycle.MutableLiveData;

import com.example.fihhuda.quran.listeningResponseModels.DataItem;
import com.example.fihhuda.quran.listeningResponseModels.ResponseModel;
import com.example.fihhuda.quran.netWorking.ApiManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListeningRebosotry {

    MutableLiveData<List<DataItem>> listOfDataItems = new MutableLiveData<>();
    public MutableLiveData<String> uriLink = new MutableLiveData<>("");

    private List<DataItem> list = new ArrayList<>();
   private String failedMessage ;
   private String uri ;
    MutableLiveData<String> message = new MutableLiveData<>("");


    public ListeningRebosotry() {
    }

 public MutableLiveData<List<DataItem>> getListOfDataItems() {
        return listOfDataItems;
    }

    public MutableLiveData<String> getMessage() {
        return message;
    }

    public void getListeningDataById_SuraName(int id , String name ){

        ApiManager.getApis().getLinkByIdAndSuraName(id, name)
                .enqueue(new Callback<ResponseModel>() {

                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {

                        if (response.isSuccessful() && response.code() == 200) {
                            ResponseModel response1 = response.body();
                              if(
                                    response1.getData().size()!=0
                              ){
                            list = response1.getData();
                            uri = response1.getData().get(0).getLink();
                            setMutableliveDataList(list, uri);



                              }} else {

                            failedMessage="failed2";
                            setMutableliveDataMessage(failedMessage);

                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                     failedMessage =t.getLocalizedMessage().toString() ;
                        // failedMassage.setValue("nooooooooo");
                        setMutableliveDataMessage(failedMessage);
                    }
                });
 }

    private void setMutableliveDataMessage(String message) {
        this.message.setValue(message);
    }

    private void setMutableliveDataList(List<DataItem> list,String uri) {
          this.listOfDataItems.setValue(list);
          //this.uriLink.setValue(uri);
         this.uriLink.postValue(uri);
    }


}
