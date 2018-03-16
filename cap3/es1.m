% nomenclatura come su libro! metticela
A = [1 0 0; 2 1 0; -1 2 1];
b = [1 2 -2];
x = sistemaTriangolareInferiore(A,b);

function [x] = sistemaTriangolareInferiore(A, b)
	x = b;
    for j=1:length(A)
        x(j)=x(j)/A(j,j);
        for i=j+1:length(A)
            x(i)=x(i)-A(i,j)*x(j);
        end
    end
end
