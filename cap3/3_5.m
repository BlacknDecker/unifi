A = [1 -1 2 2; -1  5  -14 2; 2 -14 42 2; 2 2 2 65];
%spreco memoria ma evito di imbrogliarmi, tanto sono solo esempi
A3 = A;
A4 = A;

x3 = [3.1416  2.7183  1.4142  1.7320 ]';
x4 = [2.2360  2.6457  3.1416  3.3166 ]';

b3 = A3 * x3;
b4 = A4 * x4;

% esmpio es 3, fatt. LDL^T
x3_sol = sol_es_3(algoritmo_3_6(A3), b3);
cond_A_3_2 = cond(A3, 2);
r3 = A*x3_sol - b3;
r_b_3 = (r3'*r3) / (b3'*b3);
err_3 = ((x3_sol - x3)'*(x3_sol - x3))/(x3_sol'*x3_sol);

% esmpio es 4, fatt. LU con pivoting parziale
[A4, p4] = algoritmo_3_7(A4);
x4_sol = sol_es_4(A4, p4, b4);
cond_A_4_2 = cond(A4, 2);
r4 = A*x4_sol - b4;
r_b_4 = (r4'*r4) / (b4'*b4);
err_4 = ((x4_sol - x4)'*(x4_sol - x4))/(x4_sol'*x4_sol);

function [A, p] =  algoritmo_3_7(A)
    [m,n]=size(A);
    if m~=n
        error('matrice non quadrata');
    end
    p=[1:n];
    for i=1:(n-1)
        [aki, ki] = max(abs(A(i:n, i)));
        if aki==0
            error('matrice singolare');
        end
        ki=ki+i-1;
        if ki>i
            A([i, ki], :) = A([ki, i], :);
            p([i, ki]) = p([ki, i]);
        end
        A((i+1):n, i) = A((i+1):n, i)/A(i, i);
        A((i+1):n, (i+1):n) = A((i+1):n, (i+1):n) -A((i+1):n, i)*A(i,(i+1):n);
    end
end
