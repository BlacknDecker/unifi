% caso 1
b1 = 1:12;
for i=2:12
    b1(i) = 101;
end
x1_esatto = ones(12, 1);
[x1, A] = es_3_7(100, 12, b1);

% caso 2
b2 = 0.1*b1;
x2_esatto = 0.1*ones(12, 1);
[x2, A] = es_3_7(100, 12, b2);

cond_matrice = cond(A);

% input:
%   scalari alpha ed n per specificare la matrice
%   vettore dei termini noti b
% output:
%   vettore soluzioni
%   matrice creata
function [x, A] = es_3_7(alpha, n, b)
    % creo matrice
    Vd = 1:n-1;
    for i=1:n-1
        Vd(i) = alpha;
    end
    A = eye(n,n) + diag(Vd, -1);
    % soluzione sistema
    x =  sist_triang_inf(A, b);
end
