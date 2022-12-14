package com.codegym.controller;

import com.codegym.model.Customer;
import com.codegym.model.Deposit;
import com.codegym.model.Transfer;
import com.codegym.model.dto.CustomerCreateDTO;
import com.codegym.model.dto.TransferDTO;
import com.codegym.repository.TransferRepository;
import com.codegym.service.customer.ICustomerService;
import com.codegym.service.deposit.IDepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
public class Homecontroller {
    @Autowired
    ICustomerService customerService;
    @Autowired
    private IDepositService depositService;
    @Autowired
    private TransferRepository transferRepository;

    @GetMapping("/list")
    public String showHomePage(@RequestParam(required = false) String searchKey, Model model) {
        List<Customer> customers;
        if (searchKey != null) {
            searchKey = "%" + searchKey + "%";
            customers = customerService.findAllByFullNameLikeOrEmailLikeOrPhoneLike(searchKey, searchKey, searchKey);
        } else {
            customers = customerService.findAll();
        }
        model.addAttribute("customers", customers);
        return "list";
    }
    @GetMapping("/a")
    public String showHome() {
        return "customer";
    }
    @GetMapping("/transfers")
    public String showInfoTransPage(Model model) {
        List<Transfer> transfers = transferRepository.findAll();
        BigDecimal total=new BigDecimal(0);
        for (Transfer tranfer:transfers) {
            total=total.add(tranfer.getTransactionAmount());
        }
        model.addAttribute("total", total);
        model.addAttribute("transfers", transfers);


        return "transferInfo";
    }

    @GetMapping("/create")
    public String showCreateCustomer(Model model) {
        model.addAttribute("customerCreateDTO", new CustomerCreateDTO());
        return "create";
    }

    @PostMapping("/create")
    public String save(Model model,@Validated @ModelAttribute CustomerCreateDTO customerCreateDTO, BindingResult bindingResult) {
        new CustomerCreateDTO().validate(customerCreateDTO, bindingResult);

        if (bindingResult.hasFieldErrors()) {
            model.addAttribute("errors", true);
            return "create";
        }
        Customer customer = new Customer();
        customer.setId(null);
        customer.setFullname(customerCreateDTO.getFullname());
        customer.setEmail(customerCreateDTO.getEmail());
        customer.setPhone(customerCreateDTO.getPhone());
        customer.setAddress(customerCreateDTO.getAddress());
        customer.setBalance(BigDecimal.valueOf(Long.parseLong(customerCreateDTO.getBalance())));

        customerService.save(customer);
        model.addAttribute("success", "Thêm khách hàng thành công");
        model.addAttribute("customer", new CustomerCreateDTO());
        return "create";
    }

    @GetMapping("/edit/{id}")
    public String showEditCustomer(Model model, @PathVariable long id) {
        Optional<Customer> customerOptional = customerService.findById(id);
        model.addAttribute("customer", customerOptional.get());
        return "edit";
    }

    @GetMapping("/delete/{id}")
    public String removeCustomer(@PathVariable Long id) {
        customerService.deleteById(id);
        return "redirect:/list";
    }

    @PostMapping("/edit/{id}")
    public String editCustomer(Customer customer, Model model) {
        customerService.save(customer);
        model.addAttribute("customer", customer);
        return "redirect:/list";
    }

    @GetMapping("/deposit/{cid}")
    public ModelAndView showDepositPage(@PathVariable Long cid) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/deposit");

        modelAndView.addObject("deposit", new Deposit());

        Optional<Customer> customerOptional = customerService.findById(cid);

        if (!customerOptional.isPresent()) {
            modelAndView.addObject("customer", new Customer());
            modelAndView.addObject("error", "ID khách hàng không hợp lệ");
            return modelAndView;
        }

        modelAndView.addObject("customer", customerOptional.get());

        return modelAndView;
    }
    @PostMapping("/deposit/{cid}")
    public ModelAndView deposit(@PathVariable Long cid, @Validated @ModelAttribute Deposit deposit, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/deposit");

        Optional<Customer> customerOptional = customerService.findById(cid);

        if (!customerOptional.isPresent()) {
            modelAndView.addObject("customer", new Customer());
            modelAndView.addObject("error", "ID khách hàng không hợp lệ");
            return modelAndView;
        }

        Customer customer = customerOptional.get();

        if (bindingResult.hasFieldErrors()) {
            modelAndView.addObject("customer", customer);
            modelAndView.addObject("errors", true);
            return modelAndView;
        }
        try {
            customerService.deposit(deposit, customer);
            modelAndView.addObject("deposit", new Deposit());
            modelAndView.addObject("customer", customer);
            modelAndView.addObject("success", "Gửi tiền thành công");
        } catch (Exception e) {
            modelAndView.addObject("deposit", new Deposit());
            modelAndView.addObject("customer", customer);
            modelAndView.addObject("error", "Thao tác không thành công, vui lòng liên hệ Administrator");
        }

        return modelAndView;
    }
    @GetMapping("/withdraw/{cid}")
    public ModelAndView showWithDrawPage(@PathVariable Long cid) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/withdraw");

        modelAndView.addObject("withdraw", new Deposit());

        Optional<Customer> customerOptional = customerService.findById(cid);

        if (!customerOptional.isPresent()) {
            modelAndView.addObject("customer", new Customer());
            modelAndView.addObject("error", "ID khách hàng không hợp lệ");
            return modelAndView;
        }

        modelAndView.addObject("customer", customerOptional.get());

        return modelAndView;
    }
    @PostMapping("/withdraw/{cid}")
    public ModelAndView withdraw(@PathVariable Long cid, @Validated @ModelAttribute Deposit deposit, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/withdraw");

        Optional<Customer> customerOptional = customerService.findById(cid);

        if (!customerOptional.isPresent()) {
            modelAndView.addObject("customer", new Customer());
            modelAndView.addObject("error", "ID khách hàng không hợp lệ");
            return modelAndView;
        }

        Customer customer = customerOptional.get();

        if (bindingResult.hasFieldErrors()) {
            modelAndView.addObject("customer", customer);
            modelAndView.addObject("errors", true);
            return modelAndView;
        }
        try {
            customerService.withdraw(deposit, customer);
            modelAndView.addObject("withdraw", new Deposit());
            modelAndView.addObject("customer", customer);
            modelAndView.addObject("success", "Rút tiền thành công");
        } catch (Exception e) {
            modelAndView.addObject("deposit", new Deposit());
            modelAndView.addObject("customer", customer);
            modelAndView.addObject("error", "Thao tác không thành công, vui lòng liên hệ Admin");
        }

        return modelAndView;
    }
    @GetMapping("/transfer/{cid}")
    public ModelAndView showTransferPage(@PathVariable Long cid) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/transfer");
        modelAndView.addObject("transfer", new Transfer());
        Optional<Customer> customerOptional = customerService.findById(cid);
        if (!customerOptional.isPresent()) {
            modelAndView.addObject("sender", new Customer());
            modelAndView.addObject("error", "ID khách hàng không hợp lệ");
            return modelAndView;
        }
        List<Customer> recipients = customerService.findAllAndIdNotExists(cid);
        modelAndView.addObject("recipients", recipients);
        modelAndView.addObject("sender", customerOptional.get());
        return modelAndView;
    }
    @PostMapping("/transfer/{senderId}")
    public ModelAndView transfer(@PathVariable Long senderId, @Validated @ModelAttribute TransferDTO transferDTO, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/transfer");

        if (bindingResult.hasFieldErrors()) {
            Optional<Customer> senderOptional = customerService.findById(senderId);
            modelAndView.addObject("sender", senderOptional.get());
            modelAndView.addObject("transfer", new Transfer());
            List<Customer> recipients = customerService.findAllAndIdNotExists(senderId);
            modelAndView.addObject("recipients", recipients);
            modelAndView.addObject("errors", true);
            return modelAndView;
        }
        Optional<Customer> senderOptional = customerService.findById(senderId);
        if (!senderOptional.isPresent()) {
            modelAndView.addObject("transfer", new Transfer());
            modelAndView.addObject("sender", new Customer());
            modelAndView.addObject("error", "ID người gửi không hợp lệ");
            return modelAndView;
        }
        List<Customer> recipients = customerService.findAllAndIdNotExists(senderId);

        Optional<Customer> recipient = customerService.findById(Long.parseLong(transferDTO.getRecipientId()));

        if (!recipient.isPresent()) {
            modelAndView.addObject("transfer", new Transfer());
            modelAndView.addObject("sender", senderOptional.get());
            modelAndView.addObject("recipients", recipients);
            modelAndView.addObject("error", "ID người nhận không hợp lệ");
            return modelAndView;
        }
        BigDecimal currentSenderBalance = senderOptional.get().getBalance();
        BigDecimal transferAmount = BigDecimal.valueOf(Long.parseLong(transferDTO.getTransferAmount()));
        long fees = 10;
        BigDecimal feesAmount = transferAmount.multiply(new BigDecimal(fees)).divide(new BigDecimal(100L));
        BigDecimal transactionAmount = transferAmount.add(feesAmount);

        if (currentSenderBalance.compareTo(transactionAmount) < 0) {
            modelAndView.addObject("transfer", new Transfer());
            modelAndView.addObject("sender", senderOptional.get());
            modelAndView.addObject("recipients", recipients);
            modelAndView.addObject("error", "Số dư tài khoản không đủ để thực hiện giao dịch");
            return modelAndView;
        }
        Transfer transfer = new Transfer();
        transfer.setId(0L);
        transfer.setSender(senderOptional.get());
        transfer.setRecipient(recipient.get());
        transfer.setTransferAmount(transferAmount);
        transfer.setFees(fees);
        transfer.setFeesAmount(feesAmount);
        transfer.setTransactionAmount(transactionAmount);
        customerService.transfer(transfer);
        Optional<Customer> newSenderOptional = customerService.findById(senderId);
        try {
            modelAndView.addObject("transfer", new Transfer());
            modelAndView.addObject("sender", newSenderOptional.get());
            modelAndView.addObject("recipients", recipients);
            modelAndView.addObject("success", "Chuyển khoản thành công");
        } catch (Exception e) {
            modelAndView.addObject("transfer", new Transfer());
            modelAndView.addObject("sender", newSenderOptional.get());
            modelAndView.addObject("recipients", recipients);
            modelAndView.addObject("error", "Vui lòng liên hệ Administrator");
        }
        return modelAndView;
    }


}
