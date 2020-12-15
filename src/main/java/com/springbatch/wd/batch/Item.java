/*
 * Copyright 2012-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.springbatch.wd.batch;

/**
 * @author Marcos Barbero
 * @since 2018-02-10
 */
public class Item {

    private String id;

    private Integer quantidade;
    
    private Float preco;
    
    private Float valorTotal;
    
	private Integer idVenda;
    
    public Item() {
    }

    public Item(String id, Integer quantidade,Float preco,Float valorTotal, Integer idVenda) {
        this.id = id;
        this.quantidade = quantidade;
        this.preco = preco;
        this.valorTotal = valorTotal;
        this.idVenda = idVenda;
    }

 

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Float getPreco() {
		return preco;
	}

	public void setPreco(Float preco) {
		this.preco = preco;
	}

	public Float getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(Float valorTotal) {
		this.valorTotal = valorTotal;
	}
	
	public Integer getIdVenda() {
		return idVenda;
	}

	public void setIdVenda(Integer idVenda) {
		this.idVenda = idVenda;
	}

	@Override
    public String toString() {
        return "Autobot{" +
                "id='" + id + '\'' +
                ", quantidade='" + quantidade + '\'' +
                ", preco='" + preco + '\'' +
                ", valorTotal='" + valorTotal + '\'' +
                ", idVenda='" + idVenda + '\'' +
                '}';
    }
}
