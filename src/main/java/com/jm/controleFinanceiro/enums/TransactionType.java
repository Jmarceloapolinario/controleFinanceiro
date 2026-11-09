package com.jm.controleFinanceiro.enums;

public enum TransactionType {
    INCOME,           // Receita / Entrada (Salário, Freelance, Dividendos, etc.)
    EXPENSE,          // Despesa / Saída (Alimentação, Contas de consumo, Lazer, etc.)
    TRANSFER_OUT,     // Transferência enviada (Saída de uma conta para outra)
    TRANSFER_IN,      // Transferência recebida (Entrada de uma conta vinda de outra)
    INVESTMENT,       // Aporte em investimentos (Compra de Ações, FIIs, Tesouro Direto)
    REFUND// Reembolso / Estorno (Dinheiro que entra de volta na conta)

}
