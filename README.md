LI41D-G14
=========
**Builder** -> cria classe remota para simular a chamada aos métodos através do **DAOInvocationHandler**  
**DAOInvocationHandler** -> determina a operação a ser chamada  
**DAOAbstractFunc** -> classe base para fazer operações sobre a fonte de dados (SQL)  
**DAOXxxFunc** -> concretização de uma operação concreta (uma para cada operação CRUD), são estas as funções chamadas quando for feito uma operação sobre a fonte de dados (eg getAll)
