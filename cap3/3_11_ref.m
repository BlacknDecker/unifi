x(1)=1/2;
x(2)=1/2;

tolx=10^-3; % testato da 10^-3 a 10^-6

F= @(x) [2*x(1) - x(2); 3*x(2)^2 - x(1)];
J = @(x) [2, -1; -1, 6*x(2)];

[x] = NewtonNL(F, J, x, 500, tolx, 1);

disp ('Minimo : '), disp (x)
disp ('F(x): '), disp (x(1)^4+ x(1)*( x(1)+ x(2))+(1-x(2))^2)

function [x] = NewtonNL(F,J, x, imax, tolx, out)
    i=0;
    xold = x+1;
    while (i< imax )&&( norm (x-xold )> tolx )
        i=i+1;
        xold =x;
        [L,U,P] = LUP(feval(J,x));
        x=x+solveLinearLUP(L,U, P, -feval(F,x));
        if out
            disp("incr "+norm(x-xold));
            disp("x "+x);
        end
    end
end

function [L,U,P]=LUP(A)
[m,n]=size(A);
if m~=n
    error('matrice non quadrata');
end
L=eye(n);
P=eye(n);
U=A;
for k=1:n
    [pivot, m]=max(abs(U(k:n,k)));
    if pivot==0
        error('Errore: Matrice singolare');
    end
    m=m+k-1;
        if m~=k
        % scambio le righe m e k
        U([k,m], :) = U([m, k], :);
        P([k,m], :) = P([m, k], :);
        end
        if k >= 2
            L([k,m],1:k-1) = L([m,k],1:k-1);
        end
    end
    L(k+1:n,k)=U(k+1:n,k)/U(k,k);
    U(k+1:n,:)=U(k+1:n,:)-L(k+1:n,k)*U(k,:);
end

function [b] = solveLinearLUP(L, U, P, b)
    b = TriangolareInf(L,P*b);
    b = TriangolareSup(U,b);
end

function [b] = TriangolareInf(A, b)
    for j=1:length(A)
        if A(j,j) ~= 0
            b(j) = b(j)/A(j,j);
        else
            error('le matrice è singolare')
        end
        for i=j+1:length(A)
            b(i) = b(i)-A(i,j)*b(j);
        end
    end
end


function [b] = TriangolareSup(A, b)
    for j=size(A):-1:1
        if A(j,j)==0
            error('Matrice non singolare')
        else
            b(j)=b(j)/A(j,j);
        end
        for i=1:j-1
            b(i)=b(i)-A(i,j)*b(j);
        end
    end
end

