package jpabooks.jpastore.controller;

import jpabooks.jpastore.domain.item.Book;
import jpabooks.jpastore.domain.item.Item;
import jpabooks.jpastore.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model){
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(BookForm form){
        Book book = new Book();
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setIsbn(form.getIsbn());

        itemService.saveItem(book);
        return "redirect:/";
    }

    @GetMapping("/items")
    public String list(Model model){
        List<Item> items = itemService.findItems();
        model.addAttribute("items",items);
        return "items/itemsList";
    }

    @GetMapping("items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model){
        Book item = (Book) itemService.findOne(itemId);

        BookForm form = new BookForm();  // 업데이트 할때 bookform을 보낸다.
        form.setId(item.getId());
        form.setName(item.getName());
        form.setAuthor(item.getAuthor());
        form.setPrice(item.getPrice());
        form.setIsbn(item.getIsbn());
        form.setStockQuantity(item.getStockQuantity());

        model.addAttribute("form",form);
        return "items/updateItemForm";


    }
    @PostMapping("items/{itemId}/edit")
    public String updateItem(@PathVariable Long itemId,@ModelAttribute("form") BookForm form){

        /*  어설프게 엔티티를 사용하게 된다  //변경 감지를 사용하자
        Book book = new Book();
        book.setId(form.getId());     // 준영속 상태의 객체  setId가 가능하다는 것은 이미 db에 들어 갔다 왔기 때문
        book.setName(form.getName());  // jpa 가 관리하지 않기 때문에 db에 업데이트가 일어나지 않는다.
        book.setPrice(form.getPrice());  // merge를 사용하지 않고 변경감지를 사용하는 것이 바람직하다
        book.setAuthor(form.getAuthor());
        book.setStockQuantity(form.getStockQuantity());
        book.setIsbn(form.getIsbn());
        itemService.saveItem(book);
         */
        itemService.updateItem(itemId,form.getName(),form.getPrice(), form.getStockQuantity());


        return "redirect:/items";



    }


}
