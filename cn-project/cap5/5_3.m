function If = trapad(a, b, fun, tol)
  h = (b-a)/2;
  m = (b+a)/2;
  
  If1 = h*(feval(fun, a) + feval(fun, b));
  If = If1/2 + h*feval(fun, m);
  
  err = abs(If - If1)/3;
  
  if err > tol
    % ricorsione su due meta' intervallo
    iSx = trapad(a, m, fun, tol/2);
    iDx = trapad(m, b, fun, tol/2);
    
    If = iSx + iDx;
  end
  
end

