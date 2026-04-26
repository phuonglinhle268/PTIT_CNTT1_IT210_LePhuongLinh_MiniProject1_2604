package org.example.java_web_26_4.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.java_web_26_4.dto.TodoDTO;
import org.example.java_web_26_4.model.Priority;
import org.example.java_web_26_4.model.Status;
import org.example.java_web_26_4.model.Todo;
import org.example.java_web_26_4.repository.TodoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    //thêm
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("todo", new TodoDTO());
        model.addAttribute("statuses", Status.values());
        model.addAttribute("priorities", Priority.values());
        return "form";
    }

    // update
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy task với id: " + id));


        TodoDTO dto = new TodoDTO();
        dto.setId(todo.getId());
        dto.setContent(todo.getContent());
        dto.setDueDate(todo.getDueDate());
        dto.setStatus(todo.getStatus() != null ? todo.getStatus().name() : null);
        dto.setPriority(todo.getPriority() != null ? todo.getPriority().name() : null);

        model.addAttribute("todo", dto);
        model.addAttribute("statuses", Status.values());
        model.addAttribute("priorities", Priority.values());
        return "form";
    }

    @PostMapping("/save")
    public String save(
            @Valid @ModelAttribute("todo") TodoDTO dto,
            BindingResult result,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        if (result.hasErrors()) {
            model.addAttribute("statuses", Status.values());
            model.addAttribute("priorities", Priority.values());
            return "form";
        }

        Todo todo = new Todo();
        todo.setId(dto.getId());
        todo.setContent(dto.getContent());
        todo.setDueDate(dto.getDueDate());
        todo.setStatus(dto.getStatus() != null ? Status.valueOf(dto.getStatus()) : Status.PENDING);
        todo.setPriority(dto.getPriority() != null ? Priority.valueOf(dto.getPriority()) : Priority.MEDIUM);

        todoRepository.save(todo);

        String message = dto.getId() != null ? "Cập nhật thành công" : "Thêm mới thành công";
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/";
    }


    //Flash Attribute — lưu vào Session, tự xóa sau 1 lần dùng

    @GetMapping("/delete/{id}")
    public String deleteTodo(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (!todoRepository.existsById(id)) {
            redirectAttributes.addFlashAttribute("message", "Không tìm thấy task cần xóa");
            return "redirect:/";
        }
        todoRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Xóa thành công!");
        return "redirect:/";
    }
}
