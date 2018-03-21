epsilon = 10^(-13);
A = [epsilon 1; 1 1];

A_fatt = fatt_LU(A);
L = tril(A_fatt,-1)+eye(length(A_fatt));
U = triu(A_fatt);
LU = L*U;

e = [1, 1]';
b = A*e;

gauss_senza_pivot = U\(L\b);
gauss_con_pivot = A\b;

function [A] = fatt_LU(A)
    [m,n]=size(A);

    for i=1:n-1
        if A(i,i)==0
            error("a("+i+i+") nullo!");
        end
        A(i+1:n,i) = A(i+1:n,i)/A(i,i);
        A(i+1:n,i+1:n) = A(i+1:n,i+1:n)-A(i+1:n,i)*A(i,i+1:n);
    end
end
