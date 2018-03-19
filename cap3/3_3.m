function [b] = soluzione_eserczio_3(A, b)
	b = sistemaTriangolareInferiore(tril(A,-1)+eye(length(A)), b);
	b = diagonale(diag(A), b);
	b = sistemaTriangolareSuperiore((tril(A,-1)+eye(length(A)))',b);
end

function [d] = diagonale(d,b)
    n = size(d);
    for i = 1:n
        d(i) = b(i)/d(i);
    end
end

% per colonne
function [b] = sistemaTriangolareInferiore(A, b)
    for j=1:length(A)
        b(j)=b(j)/A(j,j);
        for i=j+1:length(A)
            b(i)=b(i)-A(i,j)*b(j);
        end
    end
end

function [b] = sistemaTriangolareSuperiore(A, b)
	for j=length(A):-1:1
		b(j)=b(j)/A(j,j);
		for i=1:j-1
			b(i)=b(i)-A(i,j)*b(j);
		end
	end
end
