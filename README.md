# RestAPI
Spring boot REST API 구현

사용기술 : JPA, Spring Rest DOC, H2 DataBase
특징 : 
<div>
<img src="https://github.com/yoojadoni/RestAPI/blob/master/restdoc.jpg" width="100%" height="20%"/>
</div>

<h3> 공통 모듈화 </h1>
<p> ※ 정상적인 결과, Exception 결과 모두 일관된 포맷을 유지하기 위하여 모듈화작업 </p>
<p> 1. 오류등의 경우 ExceptionHandler사용 응답처리</p>
<p> 2. 정상적인 응답의 경우 Response 객체생성하여 응답처리</p>
    
# 데이터 형태
     {      
      "count": 데이터 수,
      "code": 응답 코드,
      "message":"응답 메시지",
      "data":"{ "totalPage" : 전체 페이지수,
                "totalCount : 전체 데이터의 수,
                "list": {
                      "id" : 1,
                      "shopId" : 1,
                      "price" : 24610,
                      "totalQuantity" : 2,
                      "status" : "ORDER_COMPLETE",
                      "orderDetail" : [ {
                                      "id" : 주문 Detail ID,
                                      "price" : 금액,
                                      "amount" : 수량,
                                      "itemId" : 상품 ID,
                                      "itemName" : "상품명"
                                      }, 
                                      {
                                      "id" : 2,
                                      "price" : 10020,
                                      "amount" : 1,
                                      "itemId" : 2,
                                      "itemName" : "안심한우 양지(1+등급) 100G/소고기"
                                      }
                               }]
       }
