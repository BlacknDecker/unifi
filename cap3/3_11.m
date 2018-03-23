x(1)=1/2;
x(2)=1/2;

tolx=10^-3; % testato da 10^-3 a 10^-6

F= @(x) [2*x(1) - x(2); 3*x(2)^2 - x(1)];
J = @(x) [2, -1; -1, 6*x(2)];

[x, i, incr] = non_lin_newton_mod(F, J, x, 500, tolx);
