@control(name="Thing", group="Thing")
  private class Test
  {
    public int multiply()
    {
      int a=1;
      int b=1;
      return(a*b);
    }
    public string printNum()
    {
      waitForStart();
        while(opModeIsActive()) {
          System.out.printIn("h");
        }
    }
  }
