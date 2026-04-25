package org.example.java_web_25_4.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.java_web_25_4.dto.TodoDTO;
import org.example.java_web_25_4.model.Priority;
import org.example.java_web_25_4.model.Status;
import org.example.java_web_25_4.model.Todo;
import org.example.java_web_25_4.repository.TodoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping
public class TodoController {

    private final TodoRepository todoRepository;

    @GetMapping
    public String listTodos(Model model) {
        model.addAttribute("todos", todoRepository.findAll());
        return "todo-list";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("todo", new TodoDTO());
        model.addAttribute("statuses", Status.values());
        model.addAttribute("priorities", Priority.values());
        return "form";
    }

    @PostMapping("/add")
    public String addList(
            @Valid @ModelAttribute("todo") TodoDTO dto,
            BindingResult result,
            Model model
    ){
        if (result.hasErrors()){
            model.addAttribute("todos", todoRepository.findAll());
            model.addAttribute("statuses", Status.values());
            model.addAttribute("priorities", Priority.values());

            return "form";
        }

        Todo todo = new Todo();
        todo.setContent(dto.getContent());
        todo.setDueDate(dto.getDueDate());
        todo.setStatus(dto.getStatus() != null ? Status.valueOf(dto.getStatus()) : Status.PENDING);
        todo.setPriority(dto.getPriority() != null ? Priority.valueOf(dto.getPriority()) : Priority.MEDIUM);

        todoRepository.save(todo);

        return "redirect:/";
    }


}
