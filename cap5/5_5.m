fun = @(x) exp(x*(-10^6));
If = double(10^(-6));
tol = 10^(-9);

[If_trapcomp, neval_trapcomp] = trapcomp_val(10^7, 0, 1, fun);
if abs(If_trapcomp-If) < tol
    disp('trapcomp - neval:')
    disp(neval_trapcomp)
    disp('trapcomp - tol:')
    disp(If_trapcomp-If)
end

[If_simpcomp, neval_simpcomp] = simpcomp_val(2*10^6, 0, 1, fun);
if abs(If_simpcomp-If) < tol
    disp('simpcomp - neval:')
    disp(neval_simpcomp)
    disp('simpcomp - tol:')
    disp(If_simpcomp-If)
end

[If_trapad, neval_trapad] = trapad_eval(0, 1, fun, tol);
disp('trapad - neval:')
disp(neval_trapad)
disp('trapad - tol:')
disp(If_trapad-If)

[If_simpad, neval_simpad] = simpad_eval(0, 1, fun, tol);
disp('simpad - neval:')
disp(neval_simpad)
disp('simpad - tol:')
disp(If_simpad-If)

% il calcolo delle valutazioni di fun in realta' e' superfluo
% servono sempre n+1 valutazioni
function [If, neval] = trapcomp_val(n, a, b, fun)
    If = 0;
    neval = 0;
    h = (b-a) / n;
    for i = 1:n-1
        If = If + fun(a + i*h);
        neval = neval + 1;
    end
    If = (h/2) * (2*If + fun(a) + fun(b));
    neval = neval + 2;
end

% anche in questo caso il calcolo delle valutazioni di fun e' superfluo
% servono sempre n+2 valutazioni
function [If, neval] = simpcomp_val(n, a, b, fun)
    If = fun(a) - fun(b);
    neval = 2;
    h = (b-a) / n;
    for i = 1:n/2
        If = If + 4*fun(a+(2*i-1)*h) + 2*fun((a+2*i*h));
        neval = neval + 2;
    end
    If = If*(h/3);
end

% tre valutazioni di fun ad ogni chiamata ricorsiva
function [If, neval] = trapad_eval(a, b, fun, tol)
    h = (b-a)/2;
    m = (b+a)/2;
  
    If1 = h*(feval(fun, a) + feval(fun, b));
    If = If1/2 + h*feval(fun, m);
    neval = 3;
  
    err = abs(If - If1) / 3;

    if err > tol
        [iSx, nSx] = trapad_eval(a, m, fun, tol/2);
        [iDx, nDx] = trapad_eval(m, b, fun, tol/2);
    
        If = iSx + iDx;
        neval = neval + nSx + nDx;
    end
end

% sei valutazioni di fun ad ogni chiamata ricorsiva
function [If, neval] = simpad_eval(a, b, fun, tol)
    h = (b-a) / 6;
    m = (a+b) / 2;
    m1 = (a+m) / 2;
    m2 = (m+b) / 2;
  
    If1 = h*(feval(fun, a) + 4*feval(fun, m) + feval(fun, b));
    If = If1/2 + h*(2*feval(fun, m1) + 2*feval(fun, m2) - feval(fun, m));
  
    neval = 6;
    err = abs(If-If1) / 15;
  
    if err > tol
        % ricorsione
        [iSx, nSx] = simpad_eval(a, m, fun, tol/2);
        [iDx, nDx] = simpad_eval(m, b, fun, tol/2);
        
        If = iSx+iDx;
        neval = neval + nSx + nDx;
    end
end

