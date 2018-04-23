%
% input:
%   n - numero di ascisse da cercare
%   a - punto iniziale dell'intervallo
%   b - punto finale dell'intervallo
% output:
%   xi - vettore con le ascisse di Chebyshev
%
function xi = ceby(n, a, b)
	xi = zeros(n+1, 1);
		for i = 0:n
			xi(n+1-i) = (a+b)/2 + cos(pi*(2*i+1)/(2*(n+1)))*(b-a)/2;
		end
end
