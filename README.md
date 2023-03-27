# RestAPI
REST API 스터디를 위한 주문 RESTful API 구현

<h2>:rocket:Setting</h2>
<div align="center" width="100%">
    <div width="300px">
        <table>
            <thead>
                <tr>
                <th align="center">환경</th>
                <th align="center">버전</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                <td align="center">Java</td>
                <td align="center">1.8</td>
                </tr>
                <tr>
                <td align="center">Spring Boot</td>
                <td align="center">2.7.6</td>
                </tr>
                <tr>
                <td align="center">Build Tool</td>
                <td align="center">Gradle</td>
                </tr>
            </tbody>
        </table>
    </div>
    <div float="left">
        <table>
            <thead>
                <tr>
                <th align="center">환경</th>
                <th align="center">버전</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                <td align="center">Java</td>
                <td align="center">1.8</td>
                </tr>
                <tr>
                <td align="center">Spring Boot</td>
                <td align="center">2.7.6</td>
                </tr>
                <tr>
                <td align="center">Build Tool</td>
                <td align="center">Gradle</td>
                </tr>
            </tbody>
        </table>
    </div>
</div>

<h3> Spring Rest DOC, jUnit 사용 TDD 및 API 문서 자동화 </h3>
 <div>
<img src="https://github.com/yoojadoni/RestAPI/blob/master/restdoc.jpg" width="50%" height="20%"/>
</div>

<h3> 공통 모듈화 </h3>

* 응답 포맷의 모듈화
    + 오류등의 경우 ExceptionHandler사용 응답처리
    + 정상적인 응답의 경우 Response 객체생성하여 응답처리
    

# 데이터 형태
### 정상 응답
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
                                          } ]
                        }
                },
       "count": "조회된 데이터의 수"
       }
   

### 비정상 응답
    {      
      "status": 상태 코드,
      "code": "응답 코드",
      "message": "에러등의 메시지"
      }

