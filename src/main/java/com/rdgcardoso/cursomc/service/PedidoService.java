package com.rdgcardoso.cursomc.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rdgcardoso.cursomc.model.ItemPedido;
import com.rdgcardoso.cursomc.model.PagamentoBoleto;
import com.rdgcardoso.cursomc.model.Pedido;
import com.rdgcardoso.cursomc.model.enums.EstadoPagamento;
import com.rdgcardoso.cursomc.repository.ItemPedidoRepository;
import com.rdgcardoso.cursomc.repository.PagamentoRepository;
import com.rdgcardoso.cursomc.repository.PedidoRepository;
import com.rdgcardoso.cursomc.service.exception.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;
	
	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
		
	public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
		 "Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}

	@Transactional
	public Pedido insert(Pedido obj) {
		//Pedido
		obj.setId(null);
		obj.setInstante(new Date());
		
		//Pagamento
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		
		if (obj.getPagamento() instanceof PagamentoBoleto) {
			PagamentoBoleto pagto = (PagamentoBoleto) obj.getPagamento();
			boletoService.preencherPagamentoBoleto(pagto, obj.getInstante());
		}
			
		//Salva Pedido
		obj = repo.save(obj);
		//Salva Pagamento
		pagamentoRepository.save(obj.getPagamento());
		
		//ItensPedidos
		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setPreco(produtoService.find(ip.getProduto().getId()).getPreco());
			ip.setPedido(obj);
		}
		//Salva ItensPedidos
		itemPedidoRepository.saveAll(obj.getItens());
		
		return obj;
	} 

}
