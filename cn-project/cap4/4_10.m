xi = [1, 1, 2, 2, 3, 3, 4, 4, 5, 5];
yi = [2.9, 3.1, 6.9, 7.1, 12.9, 13.1, 20.9, 21.1, 30.9, 31.1];

a = pol_min_q(xi, yi, 2);
p = @(t) a(1) + a(2)*t + a(3)*t^2;
fplot(p, [0, 5])
hold on
plot(xi, yi, 'r*')
hold off


function [yi] = pol_min_q(xi ,yi, m)
  xi = xi';
  if length(unique(xi)) < m+1
    error('n < m!');
  end
  
  % compongo matrice Vandermonde con xi
  V(:, m+1) = ones(length(xi), 1);
  for j = m:-1:1
    V(:,j) = xi.*V(:,j+1);
  end
  
  % specchio V e ci sono
  V_mirr = zeros(length(xi), m+1);
  for i = 1:m+1
      V_mirr(:, (m+2)-i) = V(:,i);
  end
  V = V_mirr;
  
  % risolvo con fattorizzazione QR
  % si veda esercizi capitolo 3
  yi = sol_es_8(algoritmo_3_8(V), yi');
end
