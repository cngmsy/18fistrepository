#okhttp封装
效果图gif
![]()



#okhttp封装我这里写了2种，post和get

###post请求:
/**
     * json数据为body
     */
   public void postJson(String url,String json,final HttpCallBack callBack){
           RequestBody body = RequestBody.create(JSON,json);
           final Request request = new Request.Builder().url(url).post(body).build();

           OnStart(callBack);

           client.newCall(request).enqueue(new Callback() {
               @Override
               public void onFailure(Call call, IOException e) {
                   OnError(callBack, e.getMessage());
               }
               @Override
               public void onResponse(Call call, Response response) throws IOException {
                   if (response.isSuccessful()) {
                       onSuccess(callBack, response.body().string());
                   } else {
                       OnError(callBack, response.message());
                   }
               }
           });
       }

/**
     * post请求  map是body
     *
     * @param url
     * @param map
     * @param callBack
     */
    public void postMap(String url, Map<String,String> map, final HttpCallBack callBack){
        FormBody.Builder builder=new FormBody.Builder();

        //遍历map
        if(map!=null){
            for (Map.Entry<String,String> entry : map.entrySet()){
                builder.add(entry.getKey(), entry.getValue().toString());
            }
        }
        RequestBody body = builder.build();
        Request request = new Request.Builder().url(url).post(body).build();
        OnStart(callBack);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                OnError(callBack,e.getMessage());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.isSuccessful()){
                    onSuccess(callBack,response.body().string());
                }else{
                    OnError(callBack, response.message());
                }
            }
        });
    }

###get请求:


/**
     * get 请求
     *
     * @param url
     * @param callBack
     */
  public void getJson(String url,final HttpCallBack callBack){
          Request request = new Request.Builder().url(url).build();
          OnStart(callBack);
          client.newCall(request).enqueue(new Callback() {
              @Override
              public void onFailure(Call call, IOException e) {
                  OnError(callBack,e.getMessage());
              }
              @Override
              public void onResponse(Call call, Response response) throws IOException {
                  if (response.isSuccessful()){
                      onSuccess(callBack,response.body().string());
                  }else{
                      OnError(callBack,response.message());
                  }
              }
          });
      }











