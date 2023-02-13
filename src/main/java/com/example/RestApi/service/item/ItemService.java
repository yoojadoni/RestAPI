package com.example.RestApi.service.item;

import com.example.RestApi.domain.item.Item;
import com.example.RestApi.repository.item.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class ItemService {
    @Autowired
    ItemRepository itemRepository;

    public Item findById(Long id){
        Item item = itemRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return item;
    }

    public List<Item> findAll(){
        return itemRepository.findAll();
    }


    public Item save(Item item){
        item = itemRepository.save(item);
        return item;
    }
}

