package com.example.RestApi.dto.order.common;

import com.example.RestApi.common.statuscode.StatusCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Response {
    public int code;
    public String message;
    public Object data;

    public int count;

    public Response(StatusCodeEnum statusCodeEnum, Object objData) {


        this.code = statusCodeEnum.getCode();
        this.message = statusCodeEnum.getMessage();

        if (objData instanceof List) {
            this.count = ((List<?>) objData).size();
            this.data = objData;
        } else if(objData instanceof Page){
            HashMap<String, Object> map = new HashMap<>();
            List<Page> content = new ArrayList<Page>((Collection<? extends Page>) ((Page<?>) objData).getContent());
            map.put("list", content);
            map.put("totalCount", ((Page<?>) objData).getTotalElements());
            map.put("totalPage", ((Page<?>) objData).getTotalPages());
            this.data = map;
            this.count = ((Page<?>) objData).getContent().size();
        }else {
            if(objData == null){
                this.count = 0;
            }else{
                this.data = objData;
                this.count = 1;
            }
        }
    }
}
