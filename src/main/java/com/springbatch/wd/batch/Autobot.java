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
public class Autobot {

    private String id;

    private String variavel1;
    
    private String variavel2;
    
    private String variavel3;
    
    private float totalVendedor;
    
    public Autobot() {
    }

    public Autobot(String id, String variavel1,String variavel2,String variavel3,float totalVendedor) {
        this.id = id;
        this.variavel1 = variavel1;
        this.variavel2 = variavel2;
        this.variavel3 = variavel3;
        this.totalVendedor = totalVendedor;
    }

 

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getVariavel1() {
		return variavel1;
	}

	public void setVariavel1(String variavel1) {
		this.variavel1 = variavel1;
	}

	public String getVariavel2() {
		return variavel2;
	}

	public void setVariavel2(String variavel2) {
		this.variavel2 = variavel2;
	}

	public String getVariavel3() {
		return variavel3;
	}

	public void setVariavel3(String variavel3) {
		this.variavel3 = variavel3;
	}

	public float getTotalVendedor() {
		return totalVendedor;
	}

	public void setTotalVendedor(float totalVendedor) {
		this.totalVendedor = totalVendedor;
	}

	@Override
    public String toString() {
        return "Autobot{" +
                "name='" + id + '\'' +
                ", variavel1='" + variavel1 + '\'' +
                ", variavel2='" + variavel2 + '\'' +
                ", variavel3='" + variavel3 + '\'' +
                ", totalVendedor='" + totalVendedor + '\'' +
                '}';
    }
}
