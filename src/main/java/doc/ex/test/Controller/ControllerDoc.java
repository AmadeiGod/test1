package doc.ex.test.Controller;

import doc.ex.test.Repository.RepositoryDoc;
import doc.ex.test.models.DocTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Controller
public class ControllerDoc {
    @Autowired
    private RepositoryDoc repositoryDoc;
    @GetMapping("/signup")
    public String showSignUpForm(DocTest docTest) {
        return "add-doc";
    }
    @GetMapping("/home")
    public String showUserList(Model model) {
        model.addAttribute("docs", repositoryDoc.findAll());
        return "home";
    }
    @PostMapping("/add-document")
    public String addDoc(DocTest docTest,@RequestParam("doc") MultipartFile multipartFile) throws IOException { // добавление документа
        System.out.println(docTest);
        if(repositoryDoc.findByName(docTest.getName()) == null // проверка на существующий файл
         ){
            docTest.setData(Base64.getEncoder().encodeToString(multipartFile.getBytes()).getBytes());
            repositoryDoc.save(docTest);
        }else{
            System.out.println("Такой документ уже есть");
        }
        return "redirect:/home";
    }
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) { //  изменение документа
        DocTest docTest = repositoryDoc.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid doc Id:" + id));

        model.addAttribute("docTest", docTest);
        return "update-doc-test";

    }
    @PostMapping("/update/{id}")
    public String updateBook(@PathVariable("id") long id, DocTest docTest, //  изменение документа
                             BindingResult result, Model model) {

        repositoryDoc.save(docTest);
        return "redirect:/home";
    }
    @RequestMapping(path = {"/","/search"}) // поиск документа
    public String home(DocTest docTest, Model model, String keyword,@RequestParam(defaultValue = "1") int page) {
        if(keyword != "") {
            Pageable pageable = PageRequest.of(0,3);
            List<DocTest> list = repositoryDoc.findByKeyword(keyword,pageable);
            model.addAttribute("list", list);

        }else { // если поле ввода пустое, выводим все товары
            Pageable pageable = PageRequest.of(0,15, Sort.by("name"));
            Page<DocTest> page1 = repositoryDoc.findAll(pageable);
            model.addAttribute("list", page1);}
        return "search";
    }

}
